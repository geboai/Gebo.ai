/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { TableContent, TableRow } from '@Gebo.ai/gebo-ai-rest-api';

/**
 * AI generated comments
 * 
 * Component responsible for displaying a table view from TableContent data.
 * This component renders table rows based on the input data and updates when 
 * the data changes. It uses the 'view-table.component.html' template to present
 * the table interface to the user.
 */
@Component({
    selector: 'gebo-ai-view-table-component',
    templateUrl: 'view-table.component.html',
    standalone: false
})
export class GeboAIViewTableComponent implements OnChanges{
    /**
     * Lifecycle hook that is called when any data-bound property of a directive changes.
     * In this component, it checks if the input table property has rows data and 
     * updates the tableRows property accordingly.
     * 
     * @param changes - Object containing the current and previous property values
     */
      ngOnChanges(changes: SimpleChanges): void {
        if (this.table?.rows) {
          this.tableRows=this.table.rows;
        }
      }
      
      /**
       * Input property that accepts TableContent to be displayed in the table view.
       */
      @Input() table?:TableContent;
      
      /**
       * Public property that holds the rows to be displayed in the table.
       * Initialized as an empty array and populated from the input table data.
       */
      public tableRows:TableRow[]=[];
}