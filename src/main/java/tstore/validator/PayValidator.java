package tstore.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tstore.model.AddressEntity;
import tstore.model.OrderEntity;

/**
 * Created by mipan on 19.11.2016.
 */
@Component
public class PayValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AddressEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddressEntity orderEntity = (AddressEntity) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "building", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apartment", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "NotEmpty");
    }
}
