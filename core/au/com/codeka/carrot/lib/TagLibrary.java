package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.lib.tag.BlockTag;
import au.com.codeka.carrot.lib.tag.CycleTag;
import au.com.codeka.carrot.lib.tag.ElseTag;
import au.com.codeka.carrot.lib.tag.ExtendsTag;
import au.com.codeka.carrot.lib.tag.ForTag;
import au.com.codeka.carrot.lib.tag.IfTag;
import au.com.codeka.carrot.lib.tag.IfchangedTag;
import au.com.codeka.carrot.lib.tag.IncludeTag;

public class TagLibrary extends Library<Tag> {
  public TagLibrary(Configuration config) {
    super(config, "tag");
  }

  @Override
  protected void initialize() {
    Tag extTag = new ExtendsTag();
    register(extTag.getName(), extTag);
    Tag blkTag = new BlockTag();
    register(blkTag.getName(), blkTag);
    Tag incTag = new IncludeTag();
    register(incTag.getName(), incTag);

    Tag forTag = new ForTag();
    register(forTag.getName(), forTag);
    Tag cycleTag = new CycleTag();
    register(cycleTag.getName(), cycleTag);
    Tag ifcTag = new IfchangedTag();
    register(ifcTag.getName(), ifcTag);

    Tag ifTag = new IfTag();
    register(ifTag.getName(), ifTag);
    Tag elseTag = new ElseTag();
    register(elseTag.getName(), elseTag);
  }

  public void register(Tag tag) {
    register(tag.getName(), tag);
  }
}
