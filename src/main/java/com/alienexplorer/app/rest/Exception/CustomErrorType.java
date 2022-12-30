package com.alienexplorer.app.rest.Exception;

import com.alienexplorer.app.rest.Model.Cliente;

public class CustomErrorType extends Cliente {
    private String errorMessage;
    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
