package nlp;

import java.io.IOException;

import nlp.relex.RelExContextGenerator;
import nlp.relex.RelExEventStream;
import nlp.relex.RelExSample;

import opennlp.tools.ml.maxent.GIS;
import opennlp.tools.util.Span;
import opennlp.tools.ml.maxent.GISModel;
import opennlp.tools.util.ObjectStream;

/**
 *
 * @author manal
 */
public class RelExME
{
    //default iterations
    static final int ITERATIONS = 100;
    //default cutoff
    static final int CUTOFF = 5;

    private GISModel model;

    public RelExME(GISModel model)
    {
        this.model = model;
    }

    public static GISModel train(ObjectStream<RelExSample> objectStream) throws IOException
    {
        return train(objectStream, ITERATIONS, CUTOFF);
    }

    public static GISModel train(ObjectStream<RelExSample> objectStream, int iterations, int cutoff) throws IOException
    {
        System.out.print("Getting events...");
        RelExEventStream eventStrm = new RelExEventStream(objectStream, new RelExContextGenerator());
        System.out.println("Events retreived");

        System.out.print("Training model...");
        GISModel model = GIS.trainModel(eventStrm, iterations, cutoff);
        System.out.println("model trained");

        return model;
    }

    public String eval(String[] tokens, Span parentSpan, Span childSpan)
    {
        RelExContextGenerator contextGen = new RelExContextGenerator();
        String[] context = contextGen.getContext(parentSpan, childSpan, tokens);
        String result = model.getBestOutcome(model.eval(context));

        return result;
    }
}
