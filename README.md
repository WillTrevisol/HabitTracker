# Habit Tracker App 🎯

Um aplicativo Android para rastreamento de hábitos, desenvolvido em Kotlin com arquitetura MVVM e persistência de dados local usando Room. Este projeto foi criado como parte da avaliação da disciplina de 	
Persistência de Dados (D1PDD).

---

## ✨ Funcionalidades

O aplicativo atende a todos os requisitos de um sistema CRUD completo, permitindo ao usuário gerenciar seus hábitos de forma eficiente.

* **➕ Cadastro de Hábitos (Create):** Permite criar novos hábitos, definindo nome, descrição e frequência (diária ou semanal com dias específicos).
* **📄 Listagem e Visualização (Read):** A tela principal exibe os hábitos do dia, mostrando o status de conclusão.
* **✏️ Edição de Hábitos (Update):** Permite alterar todas as informações de um hábito já existente.
* **❌ Exclusão de Hábitos (Delete):** Através de um menu de contexto (clique longo), o usuário pode deletar um hábito, e todos os seus registros históricos são removidos em cascata.
* **✔️ Acompanhamento Diário:** O usuário pode marcar um hábito como concluído apenas uma vez por dia. O status é resetado automaticamente no dia seguinte.

---
