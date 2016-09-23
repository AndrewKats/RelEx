package nlp.relex;

import java.util.Arrays;

import opennlp.tools.util.Span;

/**
 * 
 * @author manal
 */
public class RelExSample
{
    private final String output;
    private final Span parentEntity;
    private final Span childEntity;
    private final String[] tokens;

    public RelExSample(String output, Span parentEntity, Span childEntity, String[] tokens)
    {
        //method throws exception if the sample data is invalid
        validateSampleData(parentEntity, childEntity, tokens.length);

        this.output = output;
        this.parentEntity = parentEntity;
        this.childEntity = childEntity;
        this.tokens = tokens;
    }

    public Span getParentEntity()
    {
        return parentEntity;
    }

    public Span getChildEntity()
    {
        return childEntity;
    }

    public String[] getTokens()
    {
        return tokens;
    }

    public String getOutput()
    {
        return output;
    }

    //method throes exception if the start and end for parent and child span are incorrect w.r.t. number of tokens
    private static void validateSampleData(Span parentSpan, Span childSpan, int numOfTokens)
    {
        if (parentSpan.getStart() >= numOfTokens)
        {
            throw new IllegalArgumentException(
                    "Parent span start is greater than or equal to number of tokens. Parent Span: " + parentSpan.toString() + ". Num of tokens: " + numOfTokens);
        }

        if (parentSpan.getEnd() > numOfTokens)
        {
            throw new IllegalArgumentException(
                    "Parent span end is greater than the number of tokens. Parent Span: " + parentSpan.toString() + ". Num of tokens: " + numOfTokens);
        }

        if (childSpan.getStart() >= numOfTokens)
        {
            throw new IllegalArgumentException(
                    "Child span start is greater than or equal to number of tokens. Child Span: " + childSpan.toString() + ". Num of tokens: " + numOfTokens);
        }

        if (childSpan.getEnd() > numOfTokens)
        {
            throw new IllegalArgumentException(
                    "Child span end is greater than the number of tokens. Child Span: " + childSpan.toString() + ". Num of tokens: " + numOfTokens);
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else if (obj instanceof RelExSample)
        {
            RelExSample a = (RelExSample) obj;

            return getParentEntity().equals(a.getParentEntity())
                    && getChildEntity().equals(a.getChildEntity())
                    && Arrays.equals(getTokens(), a.getTokens());
        }
        else
        {
            return false;
        }
    }
}
