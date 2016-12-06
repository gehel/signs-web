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

import com.google.common.net.MediaType;
import lombok.NonNull;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Charsets.UTF_8;
import static org.springframework.web.servlet.support.RequestContextUtils.getLocale;

@Component("search.epub")
public class EpubView implements View {

    @NonNull
    private final EpubWriter epubWriter = new EpubWriter();

    @NonNull
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EpubView(@NonNull SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String getContentType() {
        return MediaType.EPUB.toString();
    }

    @Override
    public void render(
            Map<String, ?> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=signs.epub");

        Book book = new Book();
        Locale locale = getLocale(request);

        book.getMetadata().addTitle("Test ebook");
        book.getMetadata().addAuthor(new Author("some", "author"));

        for (Sign sign : (Iterable<Sign>) model.get("signs")) {

            Context context = new Context(locale);
            context.setVariable("sign", sign);

            String bookPage = templateEngine.process("bookPage", context);

            book.addSection(sign.getName(), new Resource(bookPage.getBytes(UTF_8), sign.getName() + ".html"));
            book.addResource(new Resource(sign.getImageInputStream(), sign.getImage()));
        }

        epubWriter.write(book, response.getOutputStream());
    }
}
