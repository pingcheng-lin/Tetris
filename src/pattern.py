import random
# generate the condidate pattern pool
# server will generate a random number, then client will use the number to fetch this game's pattern pool
with open('pattern.text', 'w') as f:
    for j in range(10):
        for i in range(1<<12):
            f.write(str(random.randint(1, 7)))
        f.write('\n')
