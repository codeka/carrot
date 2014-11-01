package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.lib.tag.BlockTag;
import au.com.codeka.carrot.lib.tag.CycleTag;
import au.com.codeka.carrot.lib.tag.ElseTag;
import au.com.codeka.carrot.lib.tag.ExtendsTag;
import au.com.codeka.carrot.lib.tag.ForTag;
import au.com.codeka.carrot.lib.tag.IfTag;
import au.com.codeka.carrot.lib.tag.IfchangedTag;
import au.com.codeka.carrot.lib.tag.IncludeTag;

public class TagLibrary extends SimpleLibrary<Tag> {
  private static TagLibrary lib;

  static {
    lib = new TagLibrary();
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

  public static Tag getTag(String tagName) {
    return lib.fetch(tagName);
  }

  public static void addTag(Tag tag) {
    lib.register(tag.getName(), tag);
    //JangodLogger.fine("Imported tag >>>" + tag.getName());
  }
}
