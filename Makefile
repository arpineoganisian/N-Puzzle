JAVAC = javac
JAVA = java
#-Xmx1024m
SRC_DIR = srcs/main/java
OUT_DIR = out
MAIN_CLASS = Main
FILE = srcs/main/resources/valid_boards/simple_3.txt

# Правило для сборки
all: build run

# Правило для компиляции
build:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SRC_DIR)/*.java

# Правило для запуска
run:
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS) $(FILE)

# Правило для очистки
clean:
	rm -rf $(OUT_DIR)

# Правило для пересборки
re: clean all

# Указание правил, которые не являются файлами
.PHONY: all build run clean