package br.com.caelum.calopsita.mocks;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.view.Results;

public class MockResult implements Result {

	public void include(String key, Object value) {

	}

	public <T extends View> T use(Class<T> view) {
		if (view.equals(Results.page())) {
			return view.cast(new MockedPage());
		}
		if (view.equals(Results.nothing())) {
			return null;
		}
		return view.cast(new MockedLogic());
	}

	public boolean used() {
		return false;
	}

}
