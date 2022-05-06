package com.example.batch.configuration;

//@Configuration
//@EnableBatchProcessing
//public class PartitionConfig {
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job partitioningJob() throws Exception {
//        return jobBuilderFactory.get("partitioningJob").incrementer(new RunIdIncrementer()).flow(masterStep()).end()
//                .build();
//    }
//
//    @Bean
//    public Step masterStep() throws Exception {
//        return stepBuilderFactory.get("masterStep").partitioner(slaveStep()).partitioner("partition", partitioner())
//                .gridSize(10).taskExecutor(new SimpleAsyncTaskExecutor()).build();
//    }
//
//    @Bean
//    public Partitioner partitioner() throws Exception {
//        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        partitioner.setResources(resolver.getResources("file:/home/java/meta_Clothing_Shoes_and_Jewelry/x*"));
//        return partitioner;
//    }
//
//    @Bean
//    public Step slaveStep() throws Exception {
//        return stepBuilderFactory.get("slaveStep").<JsonNode, Product>chunk(1)
//                .reader(itemReader(null)).processor(itemProcessor()).writer(itemWriter()).build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemReader<JsonNode> itemReader(@Value("#{stepExecutionContext['fileName']}") String file) {
//        return new JsonFileReader(file);
//    }
//
//    @Bean
//    public ItemProcessor<JsonNode, Product> itemProcessor() {
//        return new ProductProcessor();
//    }
//
//    @Bean
//    public ItemWriter<Product> itemWriter() {
//        return new ProductWriter();
//    }
//
//}
