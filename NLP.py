import math
import sys
from nltk.corpus import stopwords
from nltk.tokenize import RegexpTokenizer
import warnings
import os
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')
import gensim
def isSports(str):
    print("String is: ", str)
    # this line doesn't load the trained model
    from gensim.models.keyedvectors import KeyedVectors

    words = ['sports', 'sport', 'swim', 'gym', 'workout', 'run', 'jog', 'lift', 'cardio', 'exercise', 'fitness']

    # this is how you load the model
    model = KeyedVectors.load_word2vec_format('C:/wamp/GoogleNews-vectors-negative300.bin', binary=True, limit = 200000)
    # to extract word vector
    rest = 'football'
    sum = 0
    max = 0
    tmp = 0
    for word in words:
        try:
            tmp = model.similarity(word, rest)
            sum += tmp
            print(tmp)
            if tmp > max:
                max = tmp
        except Exception:
            pass
    sum = sum/len(words)
    print("avg is ", sum)
    print("max is ", max)

    str = str.lower()
    print(str)
    stop_words = set(stopwords.words('english'))
    tokenizer = RegexpTokenizer(r'\w+')
    word_tokens = tokenizer.tokenize(str)
    filtered_sentence = [w for w in word_tokens if not w in stop_words]

    print(word_tokens)
    print(filtered_sentence)
if __name__=="__main__":
    isSports(sys.argv[1])