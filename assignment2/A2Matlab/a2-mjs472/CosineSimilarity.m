function [ similarity ] = CosineSimilarity( A, B )

    sumAA = sum(A .^ 2);
    sumBB = sum(B .^ 2);
    sumAB = sum(A .* B);
    similarity = sumAB / sqrt(sumAA * sumBB);

end

