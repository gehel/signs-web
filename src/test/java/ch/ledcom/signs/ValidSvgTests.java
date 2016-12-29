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

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.xml.xpath.XPathConstants.NODESET;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidSvgTests {

    private DocumentBuilder builder;
    private XPathExpression licenseXPath;
    private XPathExpression permissionsXPath;

    @Before
    public void setupDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        builder = dbf.newDocumentBuilder();
    }

    @Before
    public void setupXPath() throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        xPath.setNamespaceContext(createNamespaceContext());

        licenseXPath = xPath.compile("/svg:svg/svg:metadata/rdf:RDF/cc:Work/cc:license/@rdf:resource");
        permissionsXPath = xPath.compile("/svg:svg/svg:metadata/rdf:RDF/cc:License/cc:permits/@rdf:resource");
    }

    @Test
    public void svgContainsCC0LicenseInfo() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        for (Resource r : svgs()) {
            try {
                containsCC0LicenseInfo(r);
            } catch (AssertionError ae) {
                throw new AssertionError(format("SVG '%s' does not have CC0 license in its metadata.", r.getFile().getAbsolutePath()), ae);
            }
        }
    }

    private void containsCC0LicenseInfo(Resource resource) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        try (InputStream in = resource.getInputStream()) {
            Document document = builder.parse(in);

            String license = licenseXPath.evaluate(document);

            assertThat(license).isEqualTo("http://creativecommons.org/publicdomain/zero/1.0/");

            List<String> permissions = readPerms((NodeList) permissionsXPath.evaluate(document, NODESET));

            assertThat(permissions)
                    .contains("http://creativecommons.org/ns#Reproduction")
                    .contains("http://creativecommons.org/ns#Distribution")
                    .contains("http://creativecommons.org/ns#DerivativeWorks");
        }
    }

    private List<String> readPerms(NodeList permissions) {
        ImmutableList.Builder<String> perms = ImmutableList.builder();
        for (int i = 0; i < permissions.getLength(); i++) perms.add(permissions.item(i).getNodeValue());
        return perms.build();
    }

    @Test
    public void svgFilenameOnlyContainsLowerCaseLettersAndOrNumbers() throws IOException {
        for (Resource r : svgs()) {
            assertThat(r.getFilename()).matches("[a-z-0-9-]*\\.svg");
        }
    }

    private Resource[] svgs() throws IOException {
        return new PathMatchingResourcePatternResolver().getResources("classpath*:ch/ledcom/signs/*.svg");
    }

    private NamespaceContext createNamespaceContext() {
        return new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                checkNotNull(prefix);
                switch (prefix) {
                    case "svg":
                        return "http://www.w3.org/2000/svg";
                    case "cc":
                        return "http://creativecommons.org/ns#";
                    case "rdf":
                        return "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
                    default:
                        return XMLConstants.NULL_NS_URI;
                }
            }

            @Override
            public String getPrefix(String s) {
                return null;
            }

            @Override
            public Iterator getPrefixes(String s) {
                return null;
            }
        };
    }

}
