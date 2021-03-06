/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.explorer.navigation;

import org.activiti.explorer.ExplorerApp;



/**
 * @author Joram Barrez
 */
public class ReportNavigator implements Navigator {

  private static final long serialVersionUID = 1L;
  
  public static final String REPORT_URI_PART = "report";
  
  public String getTrigger() {
    return REPORT_URI_PART;
  }

  public void handleNavigation(UriFragment uriFragment) {
    String reportId = uriFragment.getUriPart(1);
    
    if(reportId != null) {
      ExplorerApp.get().getViewManager().showRunReportPage(reportId);
    } else {
      ExplorerApp.get().getViewManager().showRunReportPage();
    }
  }

}
