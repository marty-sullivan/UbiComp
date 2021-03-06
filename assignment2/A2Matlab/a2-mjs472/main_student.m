%read the text file

clear;
close all
addpath('./libs/');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%construct feature vector and labels
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%fileName = './a1_990004341391757.txt'; %change the file name
fileName = './a1_353918056770072.txt'

%each row of "features" contains scan results for each wifi scan
%each row of "labels" constains scan name for each wifi scan
[ features, labels ] = ExtractFeaturesUpper( fileName );


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%add the visualization. Histrogram of the wifi access point signal strength.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
no_of_data_points = length(labels);
no_of_subplots_in_one_plot = 5;
k = 3; %first two plots are for cosine similarity and euclidian distance
for i = 1:no_of_data_points
    if rem(i-1,no_of_subplots_in_one_plot) == 0 %means we are
        figure(k)
        k = k + 1;
        l = 1;
    end
    subplot(no_of_subplots_in_one_plot,1,l); %draw 5 plots a time
    bar(-features(i,:));
    xlabel(labels{i,1});
    l = l + 1;    
end

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%find similarity (Cosine or Nearest Neigbour similarity)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%TODO: Find similarity for scans
similarity_matrix = zeros(size(features,1),size(features,1));

%features = features .^ 2
%fprintf('%d\n', size(features,1));


%
% First Attempt
%

%for i = 1:no_of_data_points
%    AC = sqrt(sum(features(i,:) .^ 2));
%    for j = 1:no_of_data_points
%        BC = sqrt(sum(features(j,:) .^ 2));
%        cov = AC * BC;
%        similarity_matrix(i,j) = sum(features(i,:) .* features(j,:)) / cov;       
%    end
%end

%
% Cleaner
%
for i = 1:no_of_data_points
    for j = 1:no_of_data_points
       similarity_matrix(i,j) = CosineSimilarity(features(i,:), features(j,:)); 
    end
end





%plot similarity
figure(1);
imagesc(similarity_matrix)
colorbar('peer', gca(), 'eastoutside');
set(gca,'xtick',1:length(labels) + 0.5);
set(gca,'xticklabel',labels);
set(gca,'ytick',1:length(labels) + 0.5);
set(gca,'yticklabel',labels);
%rotateXLabels( gca, 45 )
xticklabel_rotate([],90);
title('Cosine Similarity')

