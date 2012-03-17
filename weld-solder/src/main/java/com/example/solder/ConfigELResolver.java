/*
 * Copyright (c) 2012 Marko Milenkovic
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.example.solder;

import java.beans.FeatureDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.inject.Inject;

import org.jboss.solder.el.Resolver;
import org.slf4j.Logger;

/**
 * @see http://refcardz.dzone.com/refcardz/essential-jsp-expression#refcard-download-social-buttons-display
 * @author marko
 * 
 */
@Resolver
public class ConfigELResolver extends ELResolver {
	/** */
	private static final String CONFIGURED = "configured";
	/** */
	private static final String MAP = "map";

	private static final Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("NUM_1", "1");
		map.put("NUM_2", "2");
		map.put("KEY", "value");
	}

	@Inject
	private Logger logger;

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getValue(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object getValue(final ELContext context, final Object base, final Object property) {

		this.logger.debug("getValue() context={}, base={}, property={}", new Object[] { context, base, property });
		
		if (CONFIGURED.equals(property)) {
			context.setPropertyResolved(true);
			return "RETURN VALUE";
		}
		
		if (MAP.equals(property)) {
			context.setPropertyResolved(true);
			return map;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getType(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Class<?> getType(final ELContext context, final Object base, final Object property) {

		this.logger.debug("getType() context={}, base={}, property={}", new Object[] { context, base, property });

		if (CONFIGURED.equals(property)) {
			return String.class;
		}

		if (MAP.equals(property)) {
			return Map.class;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#setValue(javax.el.ELContext, java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setValue(final ELContext context, final Object base, final Object property, final Object value) {
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#isReadOnly(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */

	@Override
	public boolean isReadOnly(final ELContext context, final Object base, final Object property) {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getFeatureDescriptors(javax.el.ELContext, java.lang.Object)
	 */
	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(final ELContext context, final Object base) {
		this.logger.debug("getFeatureDescriptors() ..");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getCommonPropertyType(javax.el.ELContext, java.lang.Object)
	 */
	@Override
	public Class<?> getCommonPropertyType(final ELContext context, final Object base) {
		this.logger.debug("getCommonPropertyType() ..");
		return null;
	}
}
