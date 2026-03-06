import pandas as pd

# Path to your massive raw dataset
input_file = "/Users/dingleberry/Visual studio code workspace/DSC400s/DSC445/project/flight_prices.csv"     
output_file = "/Users/dingleberry/Visual studio code workspace/DSC400s/DSC445/project/flight_prices_filteredFully.csv"

chunk_size = 100000

first_chunk = True

for chunk in pd.read_csv(input_file, chunksize=chunk_size):
    filtered = chunk[
        (chunk['isBasicEconomy'] == True) &
        (chunk['isNonStop'] == True) &
        (chunk['baseFare'].notnull()) & 
        (chunk['totalFare'].notnull()) & 
        (chunk['isRefundable'] == False)
    ]

    if first_chunk:
        filtered.to_csv(output_file, index=False, mode='w')
        first_chunk = False
    else:
        filtered.to_csv(output_file, index=False, mode='a', header=False)

print("nice one goofy! saved filtered data to:", output_file)
