JAVAC 				= javac
JAVA 				= java
SRC_DIR 			= srcs/main/java
OUT_DIR 			= out
MAIN_CLASS 			= Main
GENERATOR_SCRIPT	= npuzzle-gen.py
GENERATOR_OUTPUT	= generated_input.txt
SIZE 				= 3
FLAGS 				=

# Правило для сборки
all: build run

# Правило для компиляции
build:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SRC_DIR)/*.java

# Правило для запуска
run:
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS) $(FILE)

# Правило для запуска c генерацией ввода
puzzle: build generate
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS) $(GENERATOR_OUTPUT)

# Правило для генерации ввода
generate:
	python3 $(GENERATOR_SCRIPT) $(FLAGS) $(SIZE) > $(GENERATOR_OUTPUT)

# Правило для очистки
clean:
	rm -rf $(OUT_DIR) $(GENERATOR_OUTPUT)

# Правило для пересборки
re: clean all

# Правило для пересборки с генерацией ввода
re-puzzle: clean puzzle

# Указание правил, которые не являются файлами
.PHONY: all build run clean