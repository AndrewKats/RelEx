package nlp.relex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.util.Span;

/**
 *
 * @author manal
 */
public class RelExContextGenerator
{
    public String[] getContext(Span parentEntity, Span childEntity, String[] tokens)
    {
        String peLabel, ceLabel;
        //String peHeadWords, ceHeadWords;
        String peLabelceLabel;
        String bagOfWords;

        //words in a 5-word window for both parent entity and child entity
        String pe_t_2, pe_t_1, pe_t, pe_t1, pe_t2;
        String ce_t_2, ce_t_1, ce_t, ce_t1, ce_t2;

        //entity labels
        peLabel = "peLabel=" + parentEntity.getType();
        ceLabel = "ceLabel=" + childEntity.getType();

        peLabelceLabel = "peLabelceLabel=" + peLabel + ceLabel;

        int peStart = parentEntity.getStart();
        int peEnd = parentEntity.getEnd();
        int ceStart = childEntity.getStart();
        int ceEnd = childEntity.getEnd();

        String[] bagOfWordsArr;
        if (peStart < ceStart)
        {
            bagOfWordsArr = getBagOfWords(peEnd, ceStart, tokens);
        }
        else
        {
            bagOfWordsArr = getBagOfWords(ceEnd, peStart, tokens);
        }

        //words between the two entities
        //appending every word with out a space reduces the accuracy 
        //while appending the words with a space does not have any impact on the accuracy
        bagOfWords = "bagofwords=" + Arrays.toString(bagOfWordsArr);

        if (peStart >= 2)
        {
            pe_t_2 = "pe_t_2=" + tokens[peStart - 2];
            pe_t_1 = "pe_t_1=" + tokens[peStart - 1];
        }
        else if (peStart >= 1)
        {
            pe_t_2 = "pe_t_2=" + "bos";
            pe_t_1 = "pe_t_1=" + tokens[peStart - 1];
        }
        else
        {
            pe_t_2 = "pe_t_2=" + "bos";
            pe_t_1 = "pe_t_1=" + "bos";
        }

        pe_t = "pe_t=" + tokens[peStart];

        if (peStart + 2 < tokens.length)
        {
            pe_t1 = "pe_t1=" + tokens[peStart + 1];
            pe_t2 = "pe_t2=" + tokens[peStart + 2];
        }
        else if (peStart + 1 < tokens.length)
        {
            pe_t1 = "pe_t1=" + tokens[peStart + 1];
            pe_t2 = "pe_t2=" + "eos";
        }
        else
        {
            pe_t1 = "pe_t1=" + "eos";
            pe_t2 = "pe_t2=" + "eos";
        }

        if (ceStart >= 2)
        {
            ce_t_2 = "ce_t_2=" + tokens[ceStart - 2];
            ce_t_1 = "ce_t_1=" + tokens[ceStart - 1];
        }
        else if (ceStart >= 1)
        {
            ce_t_2 = "ce_t_2=" + "bos";
            ce_t_1 = "ce_t_1=" + tokens[ceStart - 1];
        }
        else
        {
            ce_t_2 = "ce_t_2=" + "bos";
            ce_t_1 = "ce_t_1=" + "bos";
        }

        ce_t = "ce_t=" + tokens[ceStart];

        if (ceStart + 2 < tokens.length)
        {
            ce_t1 = "ce_t1=" + tokens[ceStart + 1];
            ce_t2 = "ce_t2=" + tokens[ceStart + 2];
        }
        else if (ceStart + 1 < tokens.length)
        {
            ce_t1 = "ce_t1=" + tokens[ceStart + 1];
            ce_t2 = "ce_t2=" + "eos";
        }
        else
        {
            ce_t1 = "ce_t1=" + "eos";
            ce_t2 = "ce_t2=" + "eos";
        }

        String[] features = new String[]
        {
            //labels for both the entities
            peLabel,
            ceLabel,
            peLabelceLabel,
            //words between the two entities
            bagOfWords,
            //words before and after the parent entity
            pe_t_2,
            pe_t_1,
            pe_t,
            pe_t1,
            pe_t2,
            pe_t_2 + pe_t_1,
            pe_t_1 + pe_t,
            pe_t + pe_t1,
            pe_t1 + pe_t2,
            //pe_t_2+pe_t_1+pe_t,
            //pe_t_1+pe_t+pe_t1,
            //pe_t+pe_t1+pe_t2,

            //words before and after the child entity
            ce_t_2,
            ce_t_1,
            ce_t,
            ce_t1,
            ce_t2,
            ce_t_2 + ce_t_1,
            ce_t_1 + ce_t,
            ce_t + ce_t1,
            ce_t1 + ce_t2,
        //ce_t_2+ce_t_1+ce_t,
        //ce_t_1+ce_t+ce_t1,
        //ce_t+ce_t1+ce_t2
        };

        return features;
    }

    //The token at start index is included and the token at the end index is excluded
    private String[] getBagOfWords(int start, int end, String tokens[])
    {
        ArrayList<String> tokenList = new ArrayList<>(Arrays.asList(tokens));
        List<String> bagOfWordsList = tokenList.subList(start, end);

        return bagOfWordsList.toArray(new String[bagOfWordsList.size()]);
    }
}
