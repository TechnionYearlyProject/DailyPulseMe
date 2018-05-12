import math
import sys
from nltk.corpus import stopwords
from nltk.tokenize import RegexpTokenizer
import warnings
import os
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')
import gensim
def isSports(str):
    #print("String is: ", str)
    # this line doesn't load the trained model
    from gensim.models.keyedvectors import KeyedVectors

    words = ['sports', 'sport', 'swim', 'gym', 'workout', 'run', 'jog', 'lift', 'cardio', 'exercise',
             'fitness', 'cricket', 'strength', 'powerlifting']

    # this is how you load the model
    model = KeyedVectors.load_word2vec_format('C:/wamp/GoogleNews-vectors-negative300.bin', binary=True, limit = 300000)
    # to extract word vector
    str = str.lower()
    #print(str)
    stop_words = set(stopwords.words('english'))
    tokenizer = RegexpTokenizer(r'\w+')
    word_tokens = tokenizer.tokenize(str)
    filtered_sentence = [w for w in word_tokens if not w in stop_words]
    global_max = 0
    global_avg = 0
    global_max_avg = 0
    for token in filtered_sentence:
        sum = 0
        max = 0
        tmp = 0
        for word in words:
            try:
                tmp = model.similarity(word, token)
                sum += tmp
                global_avg += tmp
                #print(tmp)
                if tmp > max:
                    max = tmp
                if tmp > global_max:
                    global_max = tmp
            except Exception:
                pass
        sum = sum/len(words)
        if sum > global_max_avg:
            global_max_avg = sum
    if global_max > 0.5:
        print("1")
    else:
        if global_max > 0.4 and global_max_avg > 0.2:
            print("1")
        else :
            if "crossfit" in filtered_sentence or "crossfitting" in filtered_sentence:
                print("1")
            else: print("0")
if __name__=="__main__":
    isSports(sys.argv[1])