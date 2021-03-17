package cdi;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Conv {

	@Inject
	Conversation conv;

	public void open() {
		if (conv.isTransient()) {
			conv.begin();
		} else {
		}
	}

	public void close() {
		if (!conv.isTransient()) {
			conv.end();
		} else {
		}

	}

}
