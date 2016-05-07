package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.lib.filter.AbsFilter;
import au.com.codeka.carrot.lib.filter.AddFilter;
import au.com.codeka.carrot.lib.filter.AndFilter;
import au.com.codeka.carrot.lib.filter.ContainFilter;
import au.com.codeka.carrot.lib.filter.CutFilter;
import au.com.codeka.carrot.lib.filter.DatetimeFilter;
import au.com.codeka.carrot.lib.filter.DefaultFilter;
import au.com.codeka.carrot.lib.filter.DivideFilter;
import au.com.codeka.carrot.lib.filter.DivisibleFilter;
import au.com.codeka.carrot.lib.filter.EqualFilter;
import au.com.codeka.carrot.lib.filter.EscapeFilter;
import au.com.codeka.carrot.lib.filter.LengthFilter;
import au.com.codeka.carrot.lib.filter.LowerFilter;
import au.com.codeka.carrot.lib.filter.Md5Filter;
import au.com.codeka.carrot.lib.filter.MultiplyFilter;
import au.com.codeka.carrot.lib.filter.NotFilter;
import au.com.codeka.carrot.lib.filter.OrFilter;
import au.com.codeka.carrot.lib.filter.ReverseFilter;
import au.com.codeka.carrot.lib.filter.TruncateFilter;
import au.com.codeka.carrot.lib.filter.UpperFilter;

public class FilterLibrary extends Library<Filter> {
  public FilterLibrary(Configuration config) {
    super(config, "filter");
  }

  @Override
  protected void initialize() {
    // common
    Filter deff = new DefaultFilter();
    register(deff.getName(), deff);

    // collection
    Filter ctnf = new ContainFilter();
    register(ctnf.getName(), ctnf);
    Filter lenf = new LengthFilter();
    register(lenf.getName(), lenf);
    Filter revf = new ReverseFilter();
    register(revf.getName(), revf);
    // Filter ranf = new RandomFilter();
    // register(ranf.getName(), ranf);

    // logic
    Filter equf = new EqualFilter();
    register(equf.getName(), equf);
    Filter notf = new NotFilter();
    register(notf.getName(), notf);
    Filter andf = new AndFilter();
    register(andf.getName(), andf);
    Filter orf = new OrFilter();
    register(orf.getName(), orf);

    // format
    Filter datf = new DatetimeFilter();
    register(datf.getName(), datf);

    // number
    Filter diaf = new DivisibleFilter();
    register(diaf.getName(), diaf);
    Filter absf = new AbsFilter();
    register(absf.getName(), absf);
    Filter addf = new AddFilter();
    register(addf.getName(), addf);
    Filter mulf = new MultiplyFilter();
    register(mulf.getName(), mulf);
    Filter divf = new DivideFilter();
    register(divf.getName(), divf);

    // string
    Filter escf = new EscapeFilter();
    register(escf.getName(), escf);
    Filter lowf = new LowerFilter();
    register(lowf.getName(), lowf);
    Filter truf = new TruncateFilter();
    register(truf.getName(), truf);
    Filter upcf = new UpperFilter();
    register(upcf.getName(), upcf);
    Filter cutf = new CutFilter();
    register(cutf.getName(), cutf);
    Filter md5f = new Md5Filter();
    register(md5f.getName(), md5f);
  }

  public void register(Filter filter) {
    register(filter.getName(), filter);
  }
}
