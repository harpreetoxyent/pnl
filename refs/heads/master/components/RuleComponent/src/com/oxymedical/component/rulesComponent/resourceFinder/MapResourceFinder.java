package com.oxymedical.component.rulesComponent.resourceFinder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.codehaus.janino.util.resource.Resource;
import org.codehaus.janino.util.resource.ResourceFinder;

/**
 * Map of the rule class resource
 * 
 * @author Oxyent Medical
 *
 */
public class MapResourceFinder extends ResourceFinder
{
  private final Map map;
  private long lastModified = 0L;

  public MapResourceFinder(Map map)
  {
    this.map = map;
  }

  public void setLastModified(long lastModified)
  {
    this.lastModified = lastModified;
  }

  public final Resource findResource(final String resourceName)
  {
    int p = resourceName.indexOf(".java");
    final String s = resourceName.substring(0, p);
    final byte[] ba = (byte[]) this.map.get(s);
    if (ba == null) return null;

    return new Resource()
    {
      public InputStream open() throws IOException
      {
        return new ByteArrayInputStream(ba);
      }

      public String getFileName()
      {
        return s;
      }

      public long lastModified()
      {
        return MapResourceFinder.this.lastModified;
      }
    };
  }
}
