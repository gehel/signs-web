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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class SignController {

    private final SignRepository signRepository;

    @Autowired
    public SignController(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    @RequestMapping(method = GET)
    public String search(
            @RequestParam(value = "q", required = false) String query,
            Model model,
            @PageableDefault(size = 8) Pageable pageable) {
        model.addAttribute("query", query);
        if (isNullOrEmpty(query)) model.addAttribute("signs", signRepository.findAll(pageable));
        else model.addAttribute("signs", signRepository.findByAllFields(query, pageable));
        return "search";
    }

    @RequestMapping(method = GET, path = "/tag/{name}")
    public String searchByTags(
            @PathVariable("name") String tag,
            Model model,
            @PageableDefault(size = 8) Pageable pageable) {
        model.addAttribute("tag", tag);
        model.addAttribute("signs", signRepository.findByTags(tag, pageable));
        return "search";
    }

    @ResponseBody
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Page<Sign> searchAPI(
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable) {
        if (isNullOrEmpty(query)) return signRepository.findAll(pageable);
        return signRepository.findByAllFields(query, pageable);
    }

    @ResponseBody
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE, path = "/tag/{name}")
    public Page<Sign> searchbyTagsAPI(
            @PathVariable("name") String tag,
            Pageable pageable) {
        return signRepository.findByTags(tag, pageable);
    }

    @RequestMapping(path = "/sign/{name}", method = GET)
    public String sign(@PathVariable("name") Sign sign, Model model) {
        model.addAttribute("sign", sign);
        return "sign";
    }

    @ResponseBody
    @RequestMapping(path = "/sign/{name}", method = GET, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<SignResource> sign(@PathVariable("name") String name) {
        SignResource signResource = SignResource.from(signRepository.findByName(name));
        signResource.add(linkTo(methodOn(SignController.class).sign(name)).withSelfRel());
        return new ResponseEntity(signResource, HttpStatus.OK);
    }

}
