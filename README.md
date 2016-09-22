# RelEX

Relation extraction built over Apache OpenNLP. This tool can be used to train and use a relation extraction model.

Reations are defined between named entities, specifically, from a parent entity to a child entity. The named entities are specified using Span objects (opennlp.tools.util.Span) and the relation between two entities is defined using a String.

# Training API
Three steps need to be followed to train a relation extractor

1. Open a sample data stream
2. Call RelEx.train
3. Save the model to a file

Functionality to create the sample data stream from a text file is not available currently and is work in progress.

Sample code
        
        RelExObjectStream stream = new RelExObjectStream(ARRAYLIST_RELEXSAMPLE);
        
        GISModel model = RelExME.train(stream);
        
        GISModelWriter writer = new SuffixSensitiveGISModelWriter(model, new File(PATH_TO_OUTPUT_FILE));
        writer.persist();

Note: positive and negative examples of relationships have to be provided in the training data. The negative relations have to be ignored when they are returned by the eval method.

# RelEx API
Use the RelExME class to find the relationship between two named entities.

        GISModelReader reader = new SuffixSensitiveGISModelReader(new File(PATH_INPUT_FILE));
        reader.checkModelType();
        GISModel model = (GISModel) reader.constructModel();
        
        RelExME relex = new RelExME(model);
        String result = relex.eval(tokens, parentEntitySpan, childEntitySpan);
