/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.llms.abstraction.layer.functions.model.VoidObject;

/**
 * Service class providing functionality to retrieve the current date and time in ISO format.
 * Implements IGToolCallbackSource to supply tool callback mechanisms.
 * 

AI generated comments
 */
@Service
public class ActualDateFunctions implements IGToolCallbackSource {

    // Constant for describing the action of getting the actual date and time in ISO format
    private static final String GET_ACTUAL_DATE_AND_TIME_IN_ISO_FORMAT = "Get actual date and time in ISO format";
    
    // Constant representing the name of the actual date time function
    private static final String ACTUAL_DATE_TIME = "actualDateTime";
    
    // Defines the category for actual date and time functions within the tool set
    static final ToolsCategory _timeCategory = new ToolsCategory("ActualDateFunctions",
            "Actual date and time functions", false);

    /**
     * Retrieves the unique identifier for this tool callback source.
     * 
     * @return the tool callback source ID.
     */
    @Override
    public String getId() {
        return "ActualDateFunctions";
    }

    /**
     * Provides the category to which this tool belongs.
     * 
     * @return the category of the tools.
     */
    @Override
    public ToolsCategory getToolCategory() {
        return _timeCategory;
    }
    
    /**
     * Constructs and returns a list of tool references.
     * This includes a reference to the functionality of getting actual date and time.
     * 
     * @return a list of ToolReference objects.
     */
    @Override
    public List<ToolReference> getFullToolReferences() {
        ToolReference reference = new ToolReference();
        reference.setName(ACTUAL_DATE_TIME);
        reference.setDescription(GET_ACTUAL_DATE_AND_TIME_IN_ISO_FORMAT);
        return List.of(reference);
    }
    
    /**
     * Inner class representing the actual date and time with components such as year, month, day, and time.
     */
    public static class ActualDateTime {
        public final int year;
        public final Month month;
        public final int dayOfMonth;
        public final DayOfWeek dayOfWeek;
        public final int hour;
        public final int minutes;
        public final int seconds;
        public final TimeZone timeZone;
        public final int weekOfYear;

        /**
         * Constructor that initializes the fields based on the current local date and time.
         */
        public ActualDateTime() {
            LocalDateTime ld = LocalDateTime.now();
            GregorianCalendar gc = new GregorianCalendar();
            year = ld.getYear();
            month = ld.getMonth();
            dayOfMonth = ld.getDayOfMonth();
            dayOfWeek = ld.getDayOfWeek();
            weekOfYear = gc.get(GregorianCalendar.WEEK_OF_YEAR);
            hour = ld.getHour();
            minutes = ld.getMinute();
            seconds = ld.getSecond();
            timeZone = gc.getTimeZone();
        }

    }

    /**
     * Creates a tool callback for obtaining the current date and time.
     * Utilizes a BiFunction that does not require parameters.
     * 
     * @return a configured ToolCallback object.
     */
    private ToolCallback create() {
        BiFunction<VoidObject, ToolContext, ActualDateTime> thisFunction = (t, c) -> {
            KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
            CalledFunction function = new CalledFunction();
            function.setFunctionName(ACTUAL_DATE_TIME);
            function.setFunctionDescription(GET_ACTUAL_DATE_AND_TIME_IN_ISO_FORMAT);
            function.setParamsDescription(List.of("No parameters"));
            if (contextVisibility != null) {
                // Adds the function call to the current interaction context if available
                contextVisibility.getCalledFunctions().add(function);
            }
            if (c != null) {
                // Registers the function call to the provided tool context
                ToolCallbackDeclarationUtil.addCallToContext(c, function);
            }
            return new ActualDateTime();
        };
        return ToolCallbackDeclarationUtil.declare(thisFunction, ACTUAL_DATE_TIME,
                GET_ACTUAL_DATE_AND_TIME_IN_ISO_FORMAT, VoidObject.class, ActualDateTime.class);
    }

    /**
     * Provides the list of tool callbacks available for this tool source.
     * 
     * @return a list of ToolCallback objects.
     */
    @Override
    public List<ToolCallback> getToolCallbacks() {
        return List.of(create());
    }

}