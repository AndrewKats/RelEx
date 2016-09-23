# RelEX

Relation extraction built over Apache OpenNLP. This tool can be used to train and use relation extraction models.

The code is compatible with Apache OpenNLP 1.6.0

Relations are defined between named entities, specifically, from a parent entity to a child entity. The named entities are specified using Span objects (opennlp.tools.util.Span) and the relation between two entities is defined using a String.

# Training API
Three steps need to be followed to train a relation extractor

1. Open a sample data stream
2. Call RelEx.train
3. Save the model to a file

Currently, the functionality to create the sample data stream from a text file is not available and is work in progress.

Sample code
        
        RelExObjectStream stream = new RelExObjectStream(ARRAYLIST_RELEXSAMPLE);
        
        GISModel model = RelExME.train(stream);
        
        GISModelWriter writer = new SuffixSensitiveGISModelWriter(model, new File(PATH_TO_OUTPUT_FILE));
        writer.persist();

Note: positive and negative examples of relationships should be provided in the training data. The negative relations are to be ignored when returned by the eval method.

# RelEx API
Use the RelExME class to find the relationship between two named entities.

        GISModelReader reader = new SuffixSensitiveGISModelReader(new File(PATH_INPUT_FILE));
        reader.checkModelType();
        GISModel model = (GISModel) reader.constructModel();
        
        RelExME relex = new RelExME(model);
        String result = relex.eval(tokens, parentEntitySpan, childEntitySpan);
