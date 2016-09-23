package nlp.relex;

import java.io.IOException;
import java.util.List;

import opennlp.tools.util.ObjectStream;

/**
 *
 * @author manal
 */
public class RelExObjectStream implements ObjectStream<RelExSample>
{
    private Integer pointer = 0;
    private List<RelExSample> sampleList;

    public RelExObjectStream(List<RelExSample> sampleList)
    {
        this.sampleList = sampleList;
    }

    @Override
    public RelExSample read() throws IOException
    {
        if (pointer < sampleList.size())
        {
            RelExSample sample = sampleList.get(pointer);
            pointer++;
            return sample;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void close() throws IOException
    {
        sampleList = null;
    }

    @Override
    public void reset()
    {
        pointer = 0;
    }
}
