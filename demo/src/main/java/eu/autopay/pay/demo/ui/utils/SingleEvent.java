package eu.autopay.pay.demo.ui.utils;

public class SingleEvent<T> {
	private final T content;
	private boolean hasBeenHandled = false;

	public SingleEvent(T content) {
		this.content = content;
	}

	public T getContentIfNotHandled() {
		if (hasBeenHandled) return null;
		hasBeenHandled = true;
		return content;
	}
}
