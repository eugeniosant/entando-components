/*
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.agiletec.plugins.jpcontentfeedback.aps.system.services.contentfeedback;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contentFeedbackConfig")
public class ContentFeedbackConfig implements IContentFeedbackConfig {

    public ContentFeedbackConfig() {
        super();
    }

    public ContentFeedbackConfig(String xml) throws Throwable {
        JAXBContext context = JAXBContext.newInstance(ContentFeedbackConfig.class);
        ContentFeedbackConfig config = (ContentFeedbackConfig) context.createUnmarshaller().unmarshal(new StringReader(xml));
        this.setComment(config.getComment());
        this.setAnonymousComment(config.getAnonymousComment());
        this.setModeratedComment(config.getModeratedComment());
        this.setRateContent(config.getRateContent());
        this.setRateComment(config.getRateComment());
    }

    public String toXML() throws Throwable {
        JAXBContext context = JAXBContext.newInstance(ContentFeedbackConfig.class);
        StringWriter sw = new StringWriter();
        context.createMarshaller().marshal(this, sw);
        return sw.toString();
    }

    public String getComment() {
        return _comment;
    }
    public void setComment(String comment) {
        this._comment = comment;
    }

    public String getAnonymousComment() {
        return _anonymousComment;
    }
    public void setAnonymousComment(String anonymousComment) {
        this._anonymousComment = anonymousComment;
    }

    public String getModeratedComment() {
		return _moderatedComment;
	}
	public void setModeratedComment(String moderatedComment) {
		this._moderatedComment = moderatedComment;
	}


	public String getRateContent() {
		return _rateContent;
	}
	public void setRateContent(String rateContent) {
		this._rateContent = rateContent;
	}

    public String getRateComment() {
        return _rateComment;
    }
    public void setRateComment(String rateComment) {
        this._rateComment = rateComment;
    }

	private String _comment;
    private String _anonymousComment;
    private String _moderatedComment;
    private String _rateContent;
    private String _rateComment;

}
