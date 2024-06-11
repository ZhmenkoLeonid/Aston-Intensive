package com.zhmenko.author.validator;

import com.zhmenko.author.model.AuthorInsertRequest;
import com.zhmenko.author.model.AuthorModifyRequest;

public class AuthorValidator {
    public boolean validate(final AuthorInsertRequest authorInsertRequest) {
        return validateName(authorInsertRequest.getFirstName(),
                authorInsertRequest.getSecondName(),
                authorInsertRequest.getThirdName());
    }

    public boolean validate(final AuthorModifyRequest authorModifyRequest, final int id) {
        return id >= 0 && authorModifyRequest.getId() == id && validateName(authorModifyRequest.getFirstName(),
                authorModifyRequest.getSecondName(),
                authorModifyRequest.getThirdName());
    }

    private boolean validateName(final String firstAuthorName,
                                 final String secondAuthorName,
                                 final String thirdAuthorName) {
        final String firstName = firstAuthorName;
        final String secondName = secondAuthorName;
        final String thirdName = thirdAuthorName;
        return firstName != null && !firstName.isEmpty() &&
               secondName != null && !secondName.isEmpty() &&
               (thirdName == null || !thirdName.isEmpty());
    }
}
