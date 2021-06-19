import random

with open('pattern.text', 'w') as f:
    for j in range(10):
        for i in range(1<<12):
            f.write(str(random.randint(1, 7)))
        f.write('\n')
