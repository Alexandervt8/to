#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "task.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void abrirTask();
    void actualizarTexto(QString textoRecibido);

private:
    Ui::MainWindow *ui;
    Task *ventanaTask;
};

#endif // MAINWINDOW_H
