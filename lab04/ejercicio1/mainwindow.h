#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QMap>
#include <QColor>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class QListWidgetItem;

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void onColorClicked(QListWidgetItem *item);

private:
    Ui::MainWindow *ui;
    QMap<QString, QColor> mapaColores;

    void prepararMapaColores();
    void poblarLista();
    void mostrarColor(const QString &nombre);
};

#endif // MAINWINDOW_H
