package net.certiv.fluentmark.editor.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class FirstColumnRule extends SingleLineRule {

	public FirstColumnRule(String startSequence, IToken token) {
		super(startSequence, null, token, '\\', true);
		setColumnConstraint(0);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		if (fColumn == UNDEFINED) return doEvaluate(scanner, resume);

		int c = scanner.read();
		scanner.unread();
		if (c == fStartSequence[0]) {
			int col = scanner.getColumn();
			if (col == 1) {
				scanner.unread();
				int p = scanner.read();
				if (p == '\n') col = 0;
			}
			return (fColumn == col ? doEvaluate(scanner, resume) : Token.UNDEFINED);
		}
		return Token.UNDEFINED;
	}
}
