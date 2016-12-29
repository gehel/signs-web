/**
 * Copyright © 2016 Guillaume Lederrey (guillaume.lederrey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.ledcom.signs;

import com.rometools.modules.opensearch.OpenSearchModule;
import com.rometools.modules.opensearch.entity.OSQuery;
import com.rometools.modules.opensearch.impl.OpenSearchModuleImpl;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.module.Module;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("search")
public class OpenSearchView extends AbstractAtomFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        super.buildFeedMetadata(model, feed, request);

        OpenSearchModule osm = new OpenSearchModuleImpl();
        OSQuery query = new OSQuery();
        query.setRole("request");
        query.setTitle("Query Title");
        query.setSearchTerms("search terms");
        query.setStartPage(0);
        query.setTotalResults(10);
        osm.addQuery(query);

        Link link = new Link();
        link.setHref("http://localhost/opensearch.xml");
        link.setRel("application/opensearchdescription+xml");
        osm.setLink(link);

        List<Module> modules = feed.getModules();
        modules.add(osm);
        feed.setModules(modules);
    }

    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Entry> entries = new ArrayList<>();

        for (Sign sign : (Iterable<Sign>) model.get("signs")) {
            Entry entry = new Entry();
            entry.setId(sign.getName());
            entry.setTitle(sign.getName());
            entry.setPublished(new Date());
            Content summary = new Content();
            summary.setValue(sign.getDefinition());
            entry.setSummary(summary);
            entries.add(entry);
        }

        return entries;
    }


}
