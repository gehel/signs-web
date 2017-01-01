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

import lombok.NonNull;
import ch.ledcom.javalib.AnkiWriter;
import ch.ledcom.javalib.Deck;
import ch.ledcom.javalib.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("search.anki")
public class AnkiView implements View {

    @NonNull
    private final AnkiWriter ankiWriter = new AnkiWriter();

    @Override
    public String getContentType() {
        return "anki+zip";
    }

    @Override
    public void render(
            Map<String, ?> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=signs.apkg");

        Deck deck = new Deck();

        for (Sign sign : (Iterable<Sign>) model.get("signs")) {

            String signName = sign.getName();
            Svg signImage = sign.getImage();

            deck.addCard(signName, new Resource(signImage));
        }

        ankiWriter.write(deck, response.getOutputStream());
    }
}
