package pl.lodz.p.pracowniaproblemowa.acodis.profile;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("profileConverter")
public class ProfileConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (value instanceof Profile) ? ((Profile) value).getLabel() : null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }

        ProfileDataBean data = context.getApplication().evaluateExpressionGet(context, "#{profileDataBean}", ProfileDataBean.class);

        for (Profile country : data.getProfiles()) {
            if (country.getLabel().equals(value)) {
                return country;
            }
        }

        throw new ConverterException(new FacesMessage(String.format("Nie wolno konwertować %s do klasy Profile", value)));
    }

}
