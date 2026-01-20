pragma circom 2.0.0;

template BracketDemo() {
    signal input matrix[3][3];
    signal output result;

    var sum = 0;
    for (var i = 0; i < 3; i++) {
        for (var j = 0; j < 3; j++) {
            if (i == j) {
                sum += matrix[i][j];
            } else {
                sum += (matrix[i][j] * 2);
            }
        }
    }

    result <== sum;
}
