package com.visitormanagementsystem.utils;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import java.util.List;

/**
 * Created by Phani.Gullapalli on 25/11/2015.
 */
public class CommonValidationErrorsRelated {
    public static String getFailedFieldNames(List<ValidationError> errors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ValidationError error : errors) {
            View view = error.getView();
            TextView textView = view instanceof FloatLabeledEditText
                    ? ((FloatLabeledEditText) view).getEditText()
                    : (TextView) view;
            List<Rule> failedRules = error.getFailedRules();
            String fieldName = textView.getHint().toString().toUpperCase().replaceAll(" ", "_");

            for (Rule failedRule : failedRules) {
                stringBuilder.append(fieldName).append(" ");
                Log.i(Rule.class.getSimpleName(), failedRule.toString());
            }
        }
        return stringBuilder.toString();
    }
}
