from hrv.utils import open_rri
import csv
import matplotlib.pyplot as plt
import numpy as np
rri = []
with open('data.csv') as csvfile:
     reader = csv.DictReader(csvfile)
     for row in reader:
         rri.append(60000 / int(row['rate']))
x = np.arange(0., len(rri))
plt.plot(x, rri)
plt.show()