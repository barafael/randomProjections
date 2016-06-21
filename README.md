Random Projections
=================
An algorithm to discover motifs(recurring contiguous subsequences) in symbolic data.

Paper by Jeremy Buhler and Martin Tompa at:
http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.571.9730&rep=rep1&type=pdf

Terminology
-----------

 * data: Long string containing instances of planted or real motifs,
 flawed by up to a specified number of differences from the model.

 * n: Length of data

 * Motif: recurring contiguous subsequence in data, containing some noise.

 * L: Length of motif searched for.

 * |c|, #c: cardinality of datastructure/collection c.

 * Noise, differences, distortions, deviations: mismatches of model and instance of motif.

 * support of a motif/model: Amount of occurrences of motif/model in data.

 * ĸ-sample of s: String that is concatenation of characters s[i] where i in ĸ. ĸ is a collection of integers.
     'Mathematical' notation: [ s[i] | i in ĸ]. Length of ĸ-sample: |k|.

 * k-similar: two strings of same length are k-similar if their k-samples are identical for sample k.

 * k: Array or collection containing up to L-d random indices where L is the length of the motif and
 d is the error tolerance.
     If there were more indices, the k-sample and the set of indices of deviations in a motif could never be disjoint.
 The indices can be in the range [0, L-1].

 * Friend: 2 strings a and  b in data are defined to be friends if they share some k-samples
     (i.e. if for some random projections, the samples of a and b are equal).
