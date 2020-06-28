# wiki_problem
You are provided with short paragraphs (no more than 5,000 characters) of text from Wikipedia, about well known places, persons, animals, flowers etc. For each paragraph, there is a set of five questions, which can be answered by reading the paragraph. Answers are of entirely factual nature, e.g. date, number, name, etc. and will be exact substrings of the paragraph. Answers could either be one word, group of words, or a complete sentence. You are also provided with the answers to each of these questions, though they are jumbled up, separated by semicolon, and provided in no specific order. Your task is to identify, which answer corresponds to which question.

# Input Format
The first line contains a short paragraph of text from Wikipedia. Lines 2 to 6 contain a total of 5 questions.
Line 7 contains all the five answers, which are jumbled up.

# Output Format
Five lines - the first line should contain the answer to the first question, second line should contain the answer to the second question, and so on and so forth.
The answer should be entirely confined to one of the possible answers, listed in the last line of the input i.e. Line 7.

# Sample Input
Zebras are several species of African equids (horse family) united by their distinctive black and white stripes. Their stripes come in different patterns, unique to each individual. They are generally social animals that live in small harems to large herds. Unlike their closest relatives, horses and donkeys, zebras have never been truly domesticated. There are three species of zebras: the plains zebra, the Grévy's zebra and the mountain zebra. The plains zebra and the mountain zebra belong to the subgenus Hippotigris, but Grévy's zebra is the sole species of subgenus Dolichohippus. The latter resembles an ass, to which it is closely related, while the former two are more
horse-like. All three belong to the genus Equus, along with other living equids. The unique stripes of zebras make them one of the animals most familiar to people. They occur in a variety of habitats, such as grasslands, savannas, woodlands, thorny scrublands, mountains, and coastal hills. However, various anthropogenic factors have had a severe impact on zebra populations, in particular hunting for skins and habitat destruction. Grévy's zebra and the mountain zebra are endangered. While plains zebras are much more plentiful, one subspecies - the Quagga - became extinct in the late 19th century. Though there is currently a plan, called the Quagga Project, that aims to breed zebras that are phenotypically similar to the Quagga, in a process called breeding back.
Which Zebras are endangered?
What is the aim of the Quagga Project?
Which animals are some of their closest relatives?
Which are the three species of zebras?
Which subgenus do the plains zebra and the mountain zebra belong to?
subgenus Hippotigris; the plains zebra, the Grévy's zebra and the mountain zebra;horses and donkeys;aims to breed zebras that are phenotypically similar to the quagga; Grévy's zebra and the mountain zebra

# Sample Output
Grévy's zebra and the mountain zebra
aims to breed zebras that are phenotypically similar to the quagga horses and donkeys
the plains zebra, the Grévy's zebra and the mountain zebra subgenus Hippotigris
