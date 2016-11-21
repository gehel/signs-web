/**
 * Copyright © ${project.inceptionYear} Guillaume Lederrey <guillaume.lederrey@gmail.com> (${email})
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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.google.common.base.Charsets.UTF_8;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/img")
public class ImageController {

    @RequestMapping(value = "/{color}", method = GET)
    @ResponseBody
    public ResponseEntity<byte[]> image(@PathVariable String color) {
        String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<svg\n" +
                "\t\txmlns=\"http://www.w3.org/2000/svg\"\n" +
                "\t\twidth=\"40mm\"\n" +
                "\t\theight=\"40mm\"\n" +
                "\t\tviewBox=\"0 0 141.73228 141.73228\"\n" +
                "\t\tid=\"svg2\"\n" +
                "\t\tversion=\"1.1\">\n" +
                "\t<rect\n" +
                "\t\t\tstyle=\"fill:#" + color + ";stroke:#000000\"\n" +
                "\t\t\tid=\"square\"\n" +
                "\t\t\twidth=\"100\"\n" +
                "\t\t\theight=\"100\"\n" +
                "\t\t\tx=\"20\"\n" +
                "\t\t\ty=\"20\"/>\n" +
                "</svg>\n";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("image/svg+xml"));

        return new ResponseEntity<>(svg.getBytes(UTF_8), headers, OK);
    }

}
