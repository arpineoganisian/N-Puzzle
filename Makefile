NAME				= npuzzle
JAVAC 				= javac
JAVA 				= java #-Xmx2g
SRC_DIR 			= srcs/main/java
OUT_DIR 			= out
MAIN_CLASS 			= NPuzzle
GENERATOR_SCRIPT	= npuzzle-gen.py
GENERATOR_OUTPUT	= generated_input.txt
SIZE 				= 3
FLAGS 				=

all: build

# Правило для компиляции
build:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SRC_DIR)/*.java

# Правило для запуска
run:
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS) $(FILE)

# Правило для запуска c генерацией ввода
puzzle: clean build generate
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS) $(GENERATOR_OUTPUT)

# Правило для генерации ввода
generate:
	python3 $(GENERATOR_SCRIPT) $(FLAGS) $(SIZE) > $(GENERATOR_OUTPUT)

# Правило для очистки
clean:
	rm -rf $(OUT_DIR) $(GENERATOR_OUTPUT)

# Правило для пересборки
re: clean all

# Указание правил, которые не являются файлами
.PHONY: all clean fclean re