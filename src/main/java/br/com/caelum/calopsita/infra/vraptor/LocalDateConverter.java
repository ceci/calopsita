package br.com.caelum.calopsita.infra.vraptor;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.joda.time.LocalDate;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.converter.LocaleBasedCalendarConverter;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.ioc.Component;

@Convert(LocalDate.class)
@Component
public class LocalDateConverter implements Converter<LocalDate> {

	private final LocaleBasedCalendarConverter delegate;
	public LocalDateConverter(RequestInfo request) {
		delegate = new LocaleBasedCalendarConverter(request);
	}

	public LocalDate convert(String value, Class<? extends LocalDate> type,
			ResourceBundle bundle) {
		Calendar calendar = delegate.convert(value, Calendar.class, bundle);
		if (calendar == null) {
            return null;
        }
        return LocalDate.fromCalendarFields(calendar);
	}

}
