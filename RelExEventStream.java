package nlp.relex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import opennlp.tools.ml.model.Event;
import opennlp.tools.util.AbstractEventStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.Span;

/**
 *
 * @author manal
 */
public class RelExEventStream extends AbstractEventStream<RelExSample>
{
    private RelExContextGenerator cg;

    public RelExEventStream(ObjectStream<RelExSample> d, RelExContextGenerator cg)
    {
        super(d);
        this.cg = cg;
    }

    @Override
    protected Iterator<Event> createEvents(RelExSample sample)
    {
        List<Event> events = new ArrayList<>();

        Span parentEntity = sample.getParentEntity();
        Span childEntity = sample.getChildEntity();
        String[] tokens = sample.getTokens();
        String output = sample.getOutput();

        events.add(new Event(output, cg.getContext(parentEntity, childEntity, tokens)));
        return events.iterator();
    }
}
