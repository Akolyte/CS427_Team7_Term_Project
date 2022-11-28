def hasVowels(strArr, queries):
    vowel = {'a', 'e', 'i', 'o', 'u'}

    def check(s):
        return (s[0] in vowel) and (s[-1] in vowel)

    n = len(strArr)
    presum = [0] * (n + 1)
    for i in range(n):
        now = check(strArr[i])
        presum[i + 1] = presum[i] + now
        print(presum)
    res = []
    for q in queries:
        line = q.split('-')
        L = int(line[0])
        R = int(line[1])
        print(L,R)
        res.append(presum[R] - presum[L - 1])
    print(res)
    return res


hasVowels(["aba", 'bcb', 'ece', 'aa', 'e'], ['1-3', '2-5', '2-2'])


