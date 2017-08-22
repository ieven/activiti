/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.activiti.explorer.ui.mainlayout;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.springframework.core.io.FileSystemResource;

import com.vaadin.ui.CustomLayout;

/**
 * @author Joram Barrez
 */
public class MainPage extends CustomLayout {

    private static final long serialVersionUID = 1L;

    public MainPage() {
        super();

        FileSystemResource resource = new FileSystemResource(
                System.getProperty("hxkj.root") + "/metronic/page/html/index.html");
        InputStream mainPageStream = null;
        try {
            mainPageStream = resource.getInputStream();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        if (mainPageStream != null) {
            try {
                initTemplateContentsFromInputStream(mainPageStream);
            }
            catch (IOException e) {
                throw new ActivitiException("Error while loading login page template from classpath resource", e);
            }
        }
        else {
            // do nothing
        }

    }

}
