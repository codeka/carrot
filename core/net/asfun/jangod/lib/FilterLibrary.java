/**********************************************************************
Copyright (c) 2009 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.lib;

import static net.asfun.jangod.util.logging.JangodLogger;

import net.asfun.jangod.lib.filter.*;

public class FilterLibrary extends SimpleLibrary<Filter>{

	private static FilterLibrary lib;
	
	static {
		lib = new FilterLibrary();
	}
	
	@Override
	protected void initialize() {
		//common
		Filter deff = new DefaultFilter();
		register(deff.getName(), deff);
		
		//collection
		Filter ctnf = new ContainFilter();
		register(ctnf.getName(), ctnf);
		Filter lenf = new LengthFilter();
		register(lenf.getName(), lenf);
//		Filter revf = new ReverseFilter();
//		register(revf.getName(), revf);
//		Filter ranf = new RandomFilter();
//		register(ranf.getName(), ranf);
		
		//logic
		Filter equf = new EqualFilter();
		register(equf.getName(), equf);
		Filter notf = new NotFilter();
		register(notf.getName(), notf);
		Filter andf = new AndFilter();
		register(andf.getName(), andf);
		Filter orf = new OrFilter();
		register(orf.getName(), orf);
		
		//format
		Filter datf = new DatetimeFilter();
		register(datf.getName(), datf);
		
		//number
		Filter diaf = new DivisibleFilter();
		register(diaf.getName(), diaf);
//		Filter absf = new AbsFilter();
//		register(absf.getName(), absf);
//		Filter addf = new AddFilter();
//		register(addf.getName(), addf);
//		Filter mulf = new MultiplyFilter();
//		register(mulf.getName(), mulf);
//		Filter divf = new DivideFilter();
//		register(divf.getName(), divf);
		
		//string
		Filter escf = new EscapeFilter();
		register(escf.getName(), escf);
		Filter lowf = new LowerFilter();
		register(lowf.getName(), lowf);
		Filter truf = new TruncateFilter();
		register(truf.getName(), truf);
		Filter upcf = new UpperFilter();
		register(upcf.getName(), upcf);
//		Filter cutf = new CutFilter();
//		register(cutf.getName(), cutf);
//		Filter md5f = new Md5Filter();
//		register(md5f.getName(), md5f);
	}
	
	public static Filter getFilter(String filterName) {
		return lib.fetch(filterName);
	}

	public static void addFilter(Filter filter) {
		lib.register(filter.getName(), filter);
		JangodLogger.fine("Imported filter >>>" + filter.getName());
	}
}
