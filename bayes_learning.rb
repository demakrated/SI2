JAR = ARGV[0] || './dist/practica2.jar'
BD_FOTOS = '../bd_fotos'

[20,30,40,50,60].each do |clusters|
	puts "\t\t=== #{clusters} ==="
	system "java -cp #{JAR} si.clustering.Quantizer #{BD_FOTOS} #{clusters}clusters100iteraciones.txt"
	system "java -cp #{JAR} si.bayes.NaiveBayesLearning #{BD_FOTOS} #{clusters} resultado-aprendizaje-#{clusters}cl.txt"
end
