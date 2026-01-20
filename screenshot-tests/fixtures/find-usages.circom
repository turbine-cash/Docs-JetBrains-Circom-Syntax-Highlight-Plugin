pragma circom 2.0.0;

template Hasher() {
    signal input left;
    signal input right;
    signal output hash;
    hash <== left * right + 1;
}

template MerkleTree(levels) {
    signal input leaf;
    signal input pathElements[levels];
    signal input pathIndices[levels];
    signal output root;

    component hashers[levels];

    signal currentHash[levels + 1];
    currentHash[0] <== leaf;

    for (var i = 0; i < levels; i++) {
        hashers[i] = Hasher();

        var isRight = pathIndices[i];
        hashers[i].left <== currentHash[i] * (1 - isRight) + pathElements[i] * isRight;
        hashers[i].right <== currentHash[i] * isRight + pathElements[i] * (1 - isRight);

        currentHash[i + 1] <== hashers[i].hash;
    }

    root <== currentHash[levels];
}

template DoubleHasher() {
    signal input value;
    signal output doubleHash;

    component h1 = Hasher();
    component h2 = Hasher();

    h1.left <== value;
    h1.right <== value;

    h2.left <== h1.hash;
    h2.right <== h1.hash;

    doubleHash <== h2.hash;
}
